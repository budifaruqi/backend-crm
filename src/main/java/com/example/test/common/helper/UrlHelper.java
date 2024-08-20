package com.example.test.common.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlHelper {

  /*
   * Case: 1
   *   s1 = "/folder/sub"
   *   s2 = "file"
   *   Expected = "folder/sub/file"
   *
   * Case: 2
   *   s1 = "folder/sub"
   *   s2 = "file"
   *   Expected = "folder/sub/file"
   * */
  public static String constructAWSFolder(String folder, String filename) {
    List<String> strings = Stream.of(folder, filename)
        .filter(StringUtils::isNotBlank)
        .collect(Collectors.toList());
    if (CollectionUtils.isNotEmpty(strings)) {
      String firstElement = strings.get(0);
      if (firstElement.startsWith("/")) {
        firstElement = firstElement.replaceFirst("/", "");
        strings.set(0, firstElement);
      }
      return StringUtils.join(strings, "/");
    } else {
      return null;
    }
  }

  /*
   * Case: 1
   *   Prefix = "http://host"
   *   Path = "path"
   *   Expected = "http://host/path
   *
   * Case: 2
   *   Prefix = "http://host"
   *   Path = "/path"
   *   Expected = "http://host/path
   * */
  public static String constructUrl(String prefix, String path) {
    return Optional.ofNullable(path)
        .filter(StringUtils::isNotBlank)
        .map(s -> {
          if (StringUtils.startsWith(path, "/")) {
            return prefix + path;
          } else {
            return prefix + "/" + path;
          }
        })
        .orElse(null);
  }

  public static String join(String... s) {
    return StringUtils.join(s, '/');
  }
}
