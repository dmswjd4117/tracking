package com.spring.boot.post.domain;

import com.spring.boot.common.BaseTime;
import com.spring.boot.like.domain.Like;
import com.spring.boot.like.domain.Likes;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.image.PostImage;
import com.spring.boot.post.domain.image.PostImages;
import com.spring.boot.post.domain.tag.PostTag;
import com.spring.boot.post.domain.tag.PostTags;
import com.spring.boot.tag.domain.Tag;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

  @Id
  @Column(name = "post_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member writer;

  @Embedded
  private PostImages postImages = new PostImages();

  @Embedded
  private PostTags postTags = new PostTags();

  private int likeTotalCount;

  public Post(String title, String content, Member writer) {
    this.title = title;
    this.content = content;
    this.writer = writer;
  }

  public void initPostTags(Set<Tag> tags) {
    postTags.init(tags, this);
  }

  public void initImages(List<String> imagePaths) {
    postImages.init(imagePaths, this);
  }

  public boolean isWrittenBy(Member member) {
    return writer.equals(member);
  }

  public void updatePost(String title, String content, Set<Tag> tags) {
    this.title = title;
    this.content = content;
    initPostTags(tags);
  }

  public void like() {
    this.likeTotalCount += 1;
  }

  public void unlike(){
    if(this.likeTotalCount - 1 < 0){
      throw new IllegalStateException();
    }
    this.likeTotalCount -= 1;
  }

  public Set<PostTag> getPostTags() {
    return postTags.getPostTags();
  }

  public List<PostImage> getPostImages() {
    return postImages.getPostImages();
  }


  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("title", title)
        .append("body", content)
        .toString();
  }
}
