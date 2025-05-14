package listsdo.todo.confing;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class APPConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
