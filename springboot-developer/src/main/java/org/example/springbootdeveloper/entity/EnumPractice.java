package org.example.springbootdeveloper.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

//공ㅣ지상항 free QNA ,evenet
@Entity
public class EnumPractice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Set을사용
    /*
    @Entity 클래스에서 컬랙션 |ENUm 타입을 데이터 베이스와 매핑하는JPA 어노테이션
    @ElementCollection (fetch =FetchType.EAGGER)
    프로트에서 useState는 사용해서 배열타입
     */
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Category> categories = new HashSet<>();

}
