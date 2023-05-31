package com.mawenhao.springbootinaction;

import com.mawenhao.springbootinaction.entity.Book;
import com.mawenhao.springbootinaction.entity.Reader;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * mock test
 *
 * @author mawenhao 2023/5/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = ReadListApplication.class)
public class MockMvcWebTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    @SneakyThrows(value = Exception.class)
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @SneakyThrows(Exception.class)
    public void homePage_unauthorized() {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "http://localhost/login"))
        ;
    }

    @SneakyThrows(Exception.class)
    @Test
    @WithUserDetails("mawenhao")
    public void homePage_authorized() {
        Reader expectedReader = Reader.builder()
                .username("mawenhao")
                .password("password")
                .fullname("Ma WenHao")
                .build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attribute("reader", samePropertyValuesAs(expectedReader)))
                .andExpect(model().attribute("books", hasSize(0)))
        ;
    }

    @Test
    @SneakyThrows(Exception.class)
    public void homePage() {
        mockMvc.perform(get("/readingList"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", is(empty())));
    }

    @Test
    @SneakyThrows(Exception.class)
    public void postBook() {
        mockMvc.perform(post("/mawenhao")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "BOOK TITLE")
                        .param("author", "BOOK AUTHOR")
                        .param("isbn", "1234567890")
                        .param("description", "BOOK DESCRIPTION"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("LOCATION", "/mawenhao"));

        Book expectedBook = Book
                .builder()
                .id(1L)
                .reader("mawenhao")
                .title("BOOK TITLE")
                .author("BOOK AUTHOR")
                .isbn("1234567890")
                .description("BOOK DESCRIPTION")
                .build();

        mockMvc.perform(get("/mawenhao"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", hasSize(1)))
                .andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook))))
        ;
    }
}
