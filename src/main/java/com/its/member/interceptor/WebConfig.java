package com.its.member.interceptor;

import com.its.member.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//해당 클래스의 설정 정보를 스프링 컨테이너에 등록
public class WebConfig implements WebMvcConfigurer {
    //인터페이스를 구현하는 클래스
    @Override //메서드 재정의의
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginCheckInterceptor())//어떤 인터셉터를 쓸래?
                .order(1)//해당 인터셉터의 우선순위
                .addPathPatterns("/**")//어떤 주소를 인터셉터로 체크 할건지(모든주소)
                .excludePathPatterns("/","/member/save_form","/member/login-form",
                        "/member/login","/member/save","/member/emailCheck");
                // 로그인을 하지 않아도 접속 가능한 주소 인터셉터를 제외 할 주소
    }

}
