package jina.hello.spring4.service;

import jina.hello.spring4.model.Member;

public interface MemberService {
    boolean saveMember(Member m);
}