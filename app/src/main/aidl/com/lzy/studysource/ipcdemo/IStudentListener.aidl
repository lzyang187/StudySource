// IStudentListener.aidl
package com.lzy.studysource.ipcdemo;

// Declare any non-default types here with import statements

import com.lzy.studysource.ipcdemo.Student;

interface IStudentListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewStudent(in Student student);
}