package com.example.test.repository.model;

import com.example.test.common.constant.CollectionName;
import com.solusinegeri.data.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = CollectionName.PARTNER)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Partner extends BaseEntity {

  @Id
  private String id;

  private String companyId;

  private String name;

  private String description;
}
