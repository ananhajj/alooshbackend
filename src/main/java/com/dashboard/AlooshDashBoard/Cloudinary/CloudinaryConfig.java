package com.dashboard.AlooshDashBoard.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "drwljtgti",
                "api_key", "398423978244954",
                "api_secret", "vd6wiSe9H5z5INGD6GMaVXvSUuM"
        ));
    }
}
