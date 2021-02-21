package org.vijayian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AService {
    //>> TODO 测试循环依赖.
    @Autowired
    private  BService bService;
}
