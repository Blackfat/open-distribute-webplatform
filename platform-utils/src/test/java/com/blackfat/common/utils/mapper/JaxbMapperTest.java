package com.blackfat.common.utils.mapper;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import com.blackfat.common.utils.mapper.JaxbMapper;

import javax.xml.bind.annotation.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

/**
 演示基于JAXB2.0的Java对象-XML转换及Dom4j的使用.
 *
 * 演示用xml如下:
 *
 * <pre>
 * <?xml version="1.0" encoding="UTF-8"?>
 * <user id="1">
 * 	<name>calvin</name>
 * 	<interests>
 * 		<interest>movie</interest>
 * 		<interest>sports</interest>
 * 	</interests>
 * </user>
 * </pre>
 */
public class JaxbMapperTest {

    @Test
    public void objectToXml() {
        User user = new User();
        user.setId(1L);
        user.setName("calvin");

        user.getInterests().add("movie");
        user.getInterests().add("sports");

        String xml = JaxbMapper.toXml(user, "UTF-8");
        System.out.println("Jaxb Object to Xml result:\n" + xml);
        assertXmlByDom4j(xml);
    }

    @Test
    public void xmlToObject() {
        String xml = generateXmlByDom4j();
        User user = JaxbMapper.fromXml(xml, User.class);

        System.out.println("Jaxb Xml to Object result:\n" + user);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getInterests()).containsOnly("movie", "sports");
    }

    /**
     * 测试以List对象作为根节点时的XML输出
     */
    @Test
    public void toXmlWithListAsRoot() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("calvin");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("kate");

        List<User> userList = Lists.newArrayList(user1, user2);

        String xml = JaxbMapper.toXml(userList, "userList", User.class, "UTF-8");
        System.out.println("Jaxb Object List to Xml result:\n" + xml);
    }

    /**
     * 使用Dom4j生成测试用的XML文档字符串.
     */
    private static String generateXmlByDom4j() {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("user").addAttribute("id", "1");

        root.addElement("name").setText("calvin");

        // List<String>
        Element interests = root.addElement("interests");
        interests.addElement("interest").addText("movie");
        interests.addElement("interest").addText("sports");

        return document.asXML();
    }

    /**
     * 使用Dom4j验证Jaxb所生成XML的正确性.
     */
    private static void assertXmlByDom4j(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            fail(e.getMessage());
        }
        Element user = doc.getRootElement();
        assertThat(user.attribute("id").getValue()).isEqualTo("1");

        Element interests = (Element) doc.selectSingleNode("//interests");
        assertThat(interests.elements()).hasSize(2);
        assertThat(((Element) interests.elements().get(0)).getText()).isEqualTo("movie");
    }

    @XmlRootElement
    // FIELD表示JAXB将自动绑定Java类中的每个非静态的（static）、非瞬态的（由@XmlTransient标 注）字段到XML
    @XmlAccessorType(XmlAccessType.FIELD)
    // 指定子节点的顺序
    @XmlType(propOrder = { "name", "interests" })
    public static class User {

        // 设置转换为xml节点中的属性
        @XmlAttribute
        private Long id;

        @XmlElement
        private String name;

        // 设置不转换为xml
        @XmlTransient
        private String password;


        // 设置对List<String>的映射, xml为<interests><interest>movie</interest></interests>
        @XmlElementWrapper(name = "interests")
        @XmlElement(name = "interest")
        private List<String> interests = Lists.newArrayList();


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }


        public List<String> getInterests() {
            return interests;
        }

        public void setInterests(List<String> interests) {
            this.interests = interests;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }
}
