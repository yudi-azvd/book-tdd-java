package com.packtpublishing.tddjava.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GradeServiceTest {
  @Test
  public void aboveOrEqual90ShouldReturnA() {
    assertEquals('A', GradeService.getLetterGrade(90));
    assertEquals('A', GradeService.getLetterGrade(91));
  }

  @Test
  public void between80And90ShouldReturnB() {
    assertEquals('B', GradeService.getLetterGrade(80));
    assertEquals('B', GradeService.getLetterGrade(89));
  }

  @Test
  public void between70And80ShouldReturnC() {
    assertEquals('C', GradeService.getLetterGrade(70));
    assertEquals('C', GradeService.getLetterGrade(79));
  }

  @Test
  public void between60And70ShouldReturnD() {
    assertEquals('D', GradeService.getLetterGrade(60));
    assertEquals('D', GradeService.getLetterGrade(69));
  }

  @Test
  public void equalOrBelow60ShouldReturnF() {
    assertEquals('F', GradeService.getLetterGrade(59));
    assertEquals('F', GradeService.getLetterGrade(58));
    assertEquals('F', GradeService.getLetterGrade(0));
  }
}
