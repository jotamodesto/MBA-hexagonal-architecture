package br.com.fullcycle.infrastructure.configurations;

import br.com.fullcycle.infrastructure.controllers.PartnerFnController;
import br.com.fullcycle.infrastructure.http.SpringHttpRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<?> routes(final PartnerFnController partnerFnController) {
        final var router = new SpringHttpRouter();
        partnerFnController.bind(router);
        return router.router().build();
    }
}
