package com.hacker.news.util

import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ParserTest {

    lateinit var testClass: TestClass

    private val testClassJson = "{\"testValue\":\"hello world\"}"

    @Before
    fun initTestClass() {
        testClass = TestClass("hello world")
    }

    @Test
    fun testParseAs() {
        Assert.assertEquals(testClass, testClassJson.parseAs<TestClass>())
    }

    @Test
    fun testTypeToken() {
        val actualResult = object : TypeToken<Any>() {

        }
        Assert.assertEquals(actualResult.type, typeToken<Any>())
    }

    @Test
    fun testParseAsTwo() {
        Assert.assertEquals(testClass, testClassJson.parseAs(TestClass::class.java))
    }

    @Test
    fun testToJson() {
        Assert.assertEquals(testClassJson, testClass.toJson())
    }
}

data class TestClass(@SerializedName("testValue") val testValue: String)