package com.example.junit5stutdy.util;

import org.springframework.stereotype.Component;

@Component // IoC컨테이너 등록
public class MailSendStub implements MailSender{
    @Override
    public boolean send() {
        return true;
    }
}
