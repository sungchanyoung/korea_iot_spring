import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//gradle에서는 기본적으로 src/test/java 폴더를 test파일의 소스 경로로 인식
// java 폴더 내의 하위 테스트 클래스에는 static import를 사용하여
//Assertions의 메서드를 호출
public class AssertJTest {
    @Test
    public void assertJTest(){
        String name1 ="이승아";
        String name2 ="이도경";
        String name3 ="김명진";

        //모든 변수가 null이 아닌지 확인
        assertThat(name1).isNotNull();
        assertThat(name2).isNotNull();
        assertThat(name3).isNotNull();
        //name1과 name2가 같은지 확인
//        assertThat(name1).isEqualTo(name2);
//        assertThat(name1).isNotEqualTo(name3);
        // name1과 name3가 다른지 확인


    }

}
