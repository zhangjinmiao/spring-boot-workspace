package com.jimzhang;

import com.alibaba.fastjson.JSON;
import com.jimzhang.domain.User;
import io.github.robwin.markup.builder.MarkupLanguage;
import io.github.robwin.swagger2markup.GroupBy;
import io.github.robwin.swagger2markup.Swagger2MarkupConverter;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import springfox.documentation.staticdocs.SwaggerResultHandler;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentationBuild {

    private String snippetDir = "target/asciidoc/generated-snippets";
    private String outputDir = "target/asciidoc";

    @Autowired
    private MockMvc mockMvc;

    @After
    public void Test() throws Exception {
        // 得到swagger.json,写入outputDir目录中
        mockMvc.perform(get("/v2/api-docs").accept(MediaType.APPLICATION_JSON))
                .andDo(SwaggerResultHandler.outputDirectory(outputDir).build())
                .andExpect(status().isOk())
                .andReturn();

        // 读取上一步生成的swagger.json转成asciiDoc,写入到outputDir
        // 这个outputDir必须和插件里面<generated></generated>标签配置一致
        Swagger2MarkupConverter.from(outputDir + "/swagger.json")
                .withPathsGroupedBy(GroupBy.TAGS)// 按tag排序
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)// 格式
                .withExamples(snippetDir)
                .build()
                .intoFolder(outputDir);// 输出
    }


    @Test
    public void TestApi() throws Exception {
        mockMvc.perform(get("/api/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("查询用户", preprocessResponse(prettyPrint())));  // '查询用户'  名字要与index.adoc 中的一致，否则 生成的文档有误

        User userInfo = new User();
        userInfo.setId(1L);
        userInfo.setName("lisi");
        userInfo.setAge(23);
        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(userInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcRestDocumentation.document("创建用户", preprocessResponse(prettyPrint())));


        mockMvc.perform(get("/api/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("获取用户列表", preprocessResponse(prettyPrint())));


        User user = new User();
        user.setId(1L);
        user.setName("张晋苗");
        user.setAge(27);
        mockMvc.perform(post("/api/users/1").contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(userInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcRestDocumentation.document("更新用户详细信息", preprocessResponse(prettyPrint())));


        mockMvc.perform(post("/api/users/delete/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("删除用户", preprocessResponse(prettyPrint())));
    }


}
