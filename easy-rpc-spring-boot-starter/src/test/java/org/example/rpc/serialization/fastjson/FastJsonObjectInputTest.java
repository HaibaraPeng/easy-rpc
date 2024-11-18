package org.example.rpc.serialization.fastjson;

import com.alibaba.fastjson.JSONObject;
import org.example.rpc.serialization.model.Organization;
import org.example.rpc.serialization.model.person.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author Roc
 * @Date 2024/11/18 15:02
 */
public class FastJsonObjectInputTest {

    private FastJsonObjectInput fastJsonObjectInput;

    @Test
    public void testReadBool() throws IOException {
        fastJsonObjectInput = new FastJsonObjectInput(new ByteArrayInputStream("true".getBytes()));
        boolean result = fastJsonObjectInput.readBool();

        assertThat(result, is(true));

        fastJsonObjectInput = new FastJsonObjectInput(new StringReader("false"));
        result = fastJsonObjectInput.readBool();

        assertThat(result, is(false));
    }

    @Test
    public void testReadByte() throws IOException {
        fastJsonObjectInput = new FastJsonObjectInput(new ByteArrayInputStream("1".getBytes()));
        byte result = fastJsonObjectInput.readByte();

        assertThat(result, is((byte) 1));
    }

    @Test
    public void testReadShort() throws IOException {
        fastJsonObjectInput = new FastJsonObjectInput(new ByteArrayInputStream("1".getBytes()));
        short result = fastJsonObjectInput.readShort();

        assertThat(result, is((short) 1));
    }

    @Test
    public void testReadInt() throws IOException {
        fastJsonObjectInput = new FastJsonObjectInput(new ByteArrayInputStream("1".getBytes()));
        int result = fastJsonObjectInput.readInt();

        assertThat(result, is(1));
    }

    @Test
    public void testReadLong() throws IOException {
        fastJsonObjectInput = new FastJsonObjectInput(new ByteArrayInputStream("1".getBytes()));
        long result = fastJsonObjectInput.readLong();

        assertThat(result, is(1L));
    }

    @Test
    public void testReadFloat() throws IOException {
        fastJsonObjectInput = new FastJsonObjectInput(new StringReader("1.23"));
        Float result = fastJsonObjectInput.readFloat();

        assertThat(result, is(1.23F));
    }

    @Test
    public void testReadDouble() throws IOException {
        fastJsonObjectInput = new FastJsonObjectInput(new StringReader("1.23"));
        Double result = fastJsonObjectInput.readDouble();

        assertThat(result, is(1.23d));
    }

    @Test
    public void testReadUTF() throws IOException {
        fastJsonObjectInput = new FastJsonObjectInput(new StringReader("\"test\""));
        String result = fastJsonObjectInput.readUTF();

        assertThat(result, is("test"));
    }

    @Test
    public void testReadBytes() throws IOException {
        fastJsonObjectInput = new FastJsonObjectInput(new ByteArrayInputStream("test".getBytes()));
        byte[] result = fastJsonObjectInput.readBytes();

        assertThat(result, is("test".getBytes()));
    }

    @Test
    public void testReadObject() throws IOException, ClassNotFoundException {
        fastJsonObjectInput = new FastJsonObjectInput(new StringReader("{ \"name\":\"John\", \"age\":30 }"));
        Person result = fastJsonObjectInput.readObject(Person.class);

        assertThat(result, not(nullValue()));
        assertThat(result.getName(), is("John"));
        assertThat(result.getAge(), is(30));
    }

    @Test
    public void testReadObjectWithoutClass() throws IOException, ClassNotFoundException {
        fastJsonObjectInput = new FastJsonObjectInput(new StringReader("{ \"name\":\"John\", \"age\":30 }"));

        JSONObject readObject = (JSONObject) fastJsonObjectInput.readObject();

        assertThat(readObject, not(nullValue()));
        assertThat(readObject.getString("name"), is("John"));
        assertThat(readObject.getInteger("age"), is(30));
    }

    @Test
    public void testEmptyLine() throws IOException, ClassNotFoundException {
        Assertions.assertThrows(EOFException.class, () -> {
            fastJsonObjectInput = new FastJsonObjectInput(new StringReader(""));

            fastJsonObjectInput.readObject();
        });
    }

    @Test
    public void testEmptySpace() throws IOException, ClassNotFoundException {
        Assertions.assertThrows(EOFException.class, () -> {
            fastJsonObjectInput = new FastJsonObjectInput(new StringReader("  "));

            fastJsonObjectInput.readObject();
        });
    }

    @Test
    public void testReadObjectWithTwoType() throws Exception {
        fastJsonObjectInput = new FastJsonObjectInput(new StringReader("[{\"name\":\"John\",\"age\":30},{\"name\":\"Born\",\"age\":24}]"));

        Method methodReturnType = getClass().getMethod("twoLayer");
        Type type = methodReturnType.getGenericReturnType();
        List<Person> o = fastJsonObjectInput.readObject(List.class, type);

        assertTrue(o instanceof List);
        assertTrue(o.get(0) instanceof Person);

        assertThat(o.size(), is(2));
        assertThat(o.get(1).getName(), is("Born"));
    }

    @Test
    public void testReadObjectWithThreeType() throws Exception {
        fastJsonObjectInput = new FastJsonObjectInput(new StringReader("{\"data\":[{\"name\":\"John\",\"age\":30},{\"name\":\"Born\",\"age\":24}]}"));

        Method methodReturnType = getClass().getMethod("threeLayer");
        Type type = methodReturnType.getGenericReturnType();
        Organization<List<Person>> o = fastJsonObjectInput.readObject(Organization.class, type);

        assertTrue(o instanceof Organization);
        assertTrue(o.getData() instanceof List);
        assertTrue(o.getData().get(0) instanceof Person);

        assertThat(o.getData().size(), is(2));
        assertThat(o.getData().get(1).getName(), is("Born"));
    }

    public List<Person> twoLayer() {
        return null;
    }

    public Organization<List<Person>> threeLayer() {
        return null;
    }
}
