package com.example.springjparelationships.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UniversityDto {  ///Malumotlarni tashish uchun xizmat qiladi
    private String name;
    private String city;
    private String district;
    private String street;
}
