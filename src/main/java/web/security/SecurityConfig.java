package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


// включает в себя @Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
    private final SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, SuccessUserHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // конфигурация для прохождения аутентификации
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //переключаемся на конкретную страницу в соответствие с ролью
                .successHandler(successUserHandler)
                // даем доступ к форме логина всем
                .permitAll();

        http
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()
                // страница аутентификации доступна всем
                .antMatchers("/", "/login").permitAll()
                // разрешаем входить на /user пользователям с ролью User и/или Admin
                .antMatchers("/user/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                // разрешаем входить на /admin пользователям с ролью Admin
                .antMatchers("/admin/**").access("hasAnyRole('ROLE_ADMIN')");

        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // задаем URL-адрес для logout (стоит по умолчанию)
                .logoutUrl("/logout")
                // после выхода отправляет в форму авторизации
                .logoutSuccessUrl("/login")
                // выключаем кроссдоменную секьюрность (на этапе обучения неважна)
                .and().csrf().disable();
    }
}
