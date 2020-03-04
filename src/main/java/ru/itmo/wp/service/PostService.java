package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.TagRepository;

import java.util.List;
import java.util.StringTokenizer;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    //private final PostRepository postRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }

    public Post makePost(PostCredentials postCredentials) {
        Post post = new Post();
        post.setTitle(postCredentials.getTitle());
        post.setText(postCredentials.getText());
        StringTokenizer st = new StringTokenizer(postCredentials.getAllTags());
        while (st.hasMoreElements()) {
            String nextToCheck = (String) st.nextElement();
            Tag tag = null;
            try {
                tag = tagRepository.findByName(nextToCheck);
            } catch (Exception e) {
                System.out.println("dont");
            }
            if (tag == null) {
                tag = new Tag(nextToCheck);
                tagRepository.save(tag);
            }
            post.addTag(tag);
        }
        return post;
    }

    public void writeComment(Post post, Comment comment, User user) {
        comment.setUser(user);
        post.addComment(comment, user);
        postRepository.save(post);
    }
}
