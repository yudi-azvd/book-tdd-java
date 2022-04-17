package com.packtpublishing.tddjava.extra;

/**
 * Exemplo de um
 * <a href="https://sam-redmond.com/why-we-shouldnt-use-tdd-fcd12992d093">
 * artigo</a> que fala mal de TDD.
 * 
 * <p>
 * 90 and above = 'A'
 * <p>
 * 80 - 90 = 'B'
 * <p>
 * 70 - 80 = 'C'
 * <p>
 * 60 - 70 = 'D'
 * <p>
 * below 60 = 'F'
 * 
 * @return A letra da equivalente à nota.
 */
public class GradeService {
  public static char getLetterGrade(int grade) {
    if (grade >= 90)
      return 'A';
    if (80 <= grade && grade < 90)
      return 'B';
    if (70 <= grade && grade < 80)
      return 'C';
    if (60 <= grade && grade < 70)
      return 'D';
    return 'F';
  }
}
