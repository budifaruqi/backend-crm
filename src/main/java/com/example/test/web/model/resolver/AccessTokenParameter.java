package com.example.test.web.model.resolver;

import com.example.test.common.vo.PermissionVO;
import com.example.test.common.vo.RoleVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessTokenParameter {

  private String accountId;

  private String companyGroupId;

  private String companyId;

  private String companyGroupIdDefault;

  private String companyIdDefault;

  private List<RoleVO> roles;

  private List<PermissionVO> permissions;

  private String name;

  private String username;

  private String acceptedRole;

  private String acceptedPermission;
}
