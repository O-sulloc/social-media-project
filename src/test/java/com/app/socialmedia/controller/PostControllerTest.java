package com.app.socialmedia.controller;

import com.app.socialmedia.domain.dto.PostAddRequest;
import com.app.socialmedia.domain.dto.PostDTO;
import com.app.socialmedia.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockUser
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    PostAddRequest request = new PostAddRequest("title test", "body test");

    //@Test
    @DisplayName("글 작성 - 성공")
    void add_success() throws Exception {
        when(postService.addPost(any(), any())).thenReturn(PostDTO.builder().postId(1L).title("title test").body("body test").build());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("글 작성 - 실패(Not a Bearer JWToken)")
    void add_failed() throws Exception {

    }

    //@Test
    @DisplayName("글 작성 - 실패(Expired JWToken)")
    void add_failed_2() throws Exception {

    }

    /*@Test
    @DisplayName("글 한 개 조회")
    void getOne() throws Exception {

        //given
        Post post = Post.builder()
                .title("test")
                .body("contents")
                .build();

        postRepository.save(post);


        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{postId}", post.getPostId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test"))
                .andExpect(jsonPath("$.body").value("body"))
                .andDo(print());

    }*/
}