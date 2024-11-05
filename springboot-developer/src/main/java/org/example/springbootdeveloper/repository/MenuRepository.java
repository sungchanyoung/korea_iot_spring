package org.example.springbootdeveloper.repository;

import org.example.springbootdeveloper.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository  extends JpaRepository<Menu ,Long> {
    //조화 결가 과 없을 경우 넝을 반환하는 대신 빈 OPTINAL객체를 NPE를 방지
    //category라는 파라미터를 받아 해당 카테고리에 속한 메뉴 객체를 리스트로 조회
    Optional<List<Menu>> findByCategory(String category);


}
