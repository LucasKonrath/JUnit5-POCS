package com.lucasdamaceno.junit5poc;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class Junit5PocApplicationTests {

	@Test
	void context_loads() {
	}

	@Test
	void test_assumption(){
		//Will always execute this.
		assumingThat(1 > 0,
				() -> assertTrue(true));

		//Will never execute this code.
		assumingThat(1 < 0,
				() -> assertTrue(false));
	}

	@Test
	public void assert_exception(){
		assertThrows(NullPointerException.class,
				() -> {
					throw new NullPointerException();
				});
	}

	@Test
	public void assert_timeout(){
		Supplier<String> supplier = () -> {
			try {
				Thread.sleep(2000l);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			return "Done";
		};

		//This will pass. It waits 2500 milliseconds for response from the supplier.
		Assertions.assertTimeout(Duration.ofMillis(2500), supplier::get);

		//This will fail, as it only waits 20ms.
		Assertions.assertTimeout(Duration.ofMillis(20), supplier::get);
	}

	@Test
	public void assert_group_all(){
		assertAll("Numbers are positive",
				() -> assertTrue(2 > 0),
				() -> assertTrue(5 > 0),
				() -> assertTrue(-2 > 0));
	}

	@Test
	@EnabledOnOs({OS.MAC})
	public void test_mac_exclusive_features(){
		assertTrue(true);
	}

	@Test
	@EnabledOnOs({OS.WINDOWS})
	public void test_windows_exclusive_features(){
		assertTrue(true);
	}

	@Test
	@EnabledIfEnvironmentVariable(named = "profile", matches = "demo")
	public void runs_only_on_demo(){
		assertTrue(true);
	}

	@Test
	@DisabledIfEnvironmentVariable(named = "profile", matches = "demo")
	public void doesnt_run_on_demo(){
		assertTrue(true);
	}

	@RepeatedTest(
			value = 10
	)
	void test_random_value(RepetitionInfo repetitionInfo){
		int random = new Random().nextInt();
		assertTrue(10 < random);
	}

	@ParameterizedTest(name = "Roman Numeral {0} equals {1}")
	@CsvSource({
			"I, 1",
			"II, 2",
			"III, 3",
			"IV, 4"
	})
	void test_roman_numerals(String romanNumeral, int value){
		assertNotNull(romanNumeral);
		assertNotNull(value);
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 7, 2, 4, 6})
	void test_odd_number(int number){
		assertTrue(number % 2 != 0);
	}

	@ParameterizedTest
	@NullSource
	void test_null(String input){
		assertNull(input);
	}

	@ParameterizedTest
	@EmptySource
	void test_empty_string(String input){
		assertTrue(Strings.isBlank(input));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"  ", "\t", "\n"})
	void is_blank_should_return_true_for_all_types_of_blank_strings(String input) {
		assertTrue(Strings.isBlank(input));
	}

	@ParameterizedTest
	@EnumSource(DayOfWeek.class)
	void list_days_of_week(DayOfWeek dayOfWeek){
		assertTrue(dayOfWeek.getValue() >= 1 && dayOfWeek.getValue() <= 7);
	}

	@ParameterizedTest
	@EnumSource(value = DayOfWeek.class, names = {"SATURDAY", "SUNDAY"})
	void list_saturday_and_sunday(DayOfWeek dayOfWeek){
		assertTrue(dayOfWeek.getValue() == 6 || dayOfWeek.getValue() == 7);
	}

	@ParameterizedTest
	@MethodSource("provideFibonacciSequence")
	void show_method_source(Integer fib){
		assertNotNull(fib);
	}

	static Stream<Arguments> provideFibonacciSequence(){
		return Stream.of(
				Arguments.of(0),
				Arguments.of(1),
				Arguments.of(1),
				Arguments.of(2),
				Arguments.of(3)
		);
	}

	@ParameterizedTest
	@CsvSource({"2018/12/25,2018", "2019/02/11,2019"})
	void getYear_ShouldWorkAsExpected(
			@ConvertWith(SlashyDateConverter.class) LocalDate date, int expected) {
		assertEquals(expected, date.getYear());
	}


	@TestFactory
	Collection<DynamicTest> dynamic_tests(){
		return asList(
				DynamicTest.dynamicTest("First test",
						() -> assertEquals(1, 1)),
				DynamicTest.dynamicTest("Second test",
						() -> assertTrue(true))
		);
	}

	@TestFactory
	Stream<DynamicTest> dynamicTestsFromIntStream() {
		return IntStream.iterate(0, n -> n + 2).limit(10)
				.mapToObj(n -> DynamicTest.dynamicTest("test" + n,
						() -> assertTrue(n % 2 == 0)));
	}

}
