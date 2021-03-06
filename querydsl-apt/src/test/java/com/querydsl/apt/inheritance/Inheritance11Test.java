/*
 * Copyright 2015, The Querydsl Team (http://www.querydsl.com/team)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.querydsl.apt.inheritance;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.querydsl.core.annotations.QueryEntity;

public class Inheritance11Test {

    @QueryEntity
    public class Foo extends FooBase<Foo> {

    }

    @QueryEntity
    public class FooBase<T> {

    }

    @QueryEntity
    public class BarBase<T> {

    }

    @QueryEntity
    public class Bar extends BarBase<Foo> {

    }

    @Test
    public void test() {
        assertNotNull(QInheritance11Test_Foo.Constants.foo);
        assertNotNull(QInheritance11Test_FooBase.Constants.fooBase);
        assertNotNull(QInheritance11Test_Bar.Constants.bar);
        assertNotNull(QInheritance11Test_BarBase.Constants.barBase);
    }

}
