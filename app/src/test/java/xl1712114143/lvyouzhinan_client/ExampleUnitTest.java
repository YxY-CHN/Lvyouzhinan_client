package xl1712114143.lvyouzhinan_client;

import com.google.gson.Gson;

import org.junit.Test;

import xl1712114143.lvyouzhinan_client.bean.User;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String result = "{\"id\":1,\"name\":\"user2\",\"phone\":123,\"password\":\"123\"}";
        Gson gson = new Gson();
        User user = gson.fromJson(result, User.class);
        System.out.println(user.getName());
        System.out.println(user.getPassword());
    }
}