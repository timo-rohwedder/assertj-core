/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2020 the original author or authors.
 */
package org.assertj.core.api.recursive.comparison;

import static org.apache.commons.lang3.RandomStringUtils.randomAscii;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.util.Lists.list;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("DualValue hasSamePairOfValuesAs")
public class DualValue_hasSamePairOfValuesAs_Test {

  @ParameterizedTest(name = "actual {0} / expected {1}")
  @MethodSource("matchingValues")
  void should_return_true_when_values_are_the_same(Object actual, Object expected) {
    // GIVEN
    DualValue dualValue1 = new DualValue(randomPath(), actual, expected);
    DualValue dualValue2 = new DualValue(randomPath(), actual, expected);
    // WHEN
    boolean samePairOfValues = dualValue1.hasSamePairOfValuesAs(dualValue2);
    // THEN
    then(samePairOfValues).isTrue();
  }

  static Stream<Arguments> matchingValues() {
    return Stream.of(Arguments.of(list("foo"), list(1, 2)),
                     Arguments.of("abc", Optional.of("foo")),
                     Arguments.of(new Object(), new Object()),
                     Arguments.of(null, null),
                     Arguments.of("abc", null),
                     Arguments.of(null, "abc"),
                     Arguments.of("abc", "abc"));
  }

  @ParameterizedTest(name = "({0}, {1}) / ({2}, {3})")
  @MethodSource("nonMatchingValues")
  void should_return_false_when_values_are_not_the_same(Object actual1, Object expected1, Object actual2, Object expected2) {
    // GIVEN
    DualValue dualValue1 = new DualValue(randomPath(), actual1, expected1);
    DualValue dualValue2 = new DualValue(randomPath(), actual2, expected2);
    // WHEN
    boolean samePairOfValues = dualValue1.hasSamePairOfValuesAs(dualValue2);
    // THEN
    then(samePairOfValues).isFalse();
  }

  static Stream<Arguments> nonMatchingValues() {
    return Stream.of(Arguments.of(list("foo"), list(1, 2), list("foo"), list(1, 2)),
                     Arguments.of(new Object(), new Object(), new Object(), new Object()),
                     Arguments.of("abc", new String("abc"), "abc", new String("abc")),
                     Arguments.of(null, "abc", null, new String("abc")),
                     Arguments.of(new String("abc"), null, "abc", null));
  }

  private static List<String> randomPath() {
    return list(randomAscii(3), randomAscii(3));
  }

}
