参考：
http://gitbook.cn/gitchat/column/59f5daa149cd4330613605ba/topic/59f97f0d68673133615f7481

##有序集合
Redis sorted set 的内部使用 HashMap 和跳跃表（SkipList）来保证数据的存储和有序，HashMap 里放的是成员到 score 的映射，而跳跃表里存放的是所有的成员，排序依据是 HashMap 里存的 score，使用跳跃表的结构可以获得比较高的查找效率，并且在实现上比较简单。