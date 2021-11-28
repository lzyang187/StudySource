// IStudentManager.aidl
package com.lzy.studysource.ipcdemo;

// Declare any non-default types here with import statements
import com.lzy.studysource.ipcdemo.Student;
import com.lzy.studysource.ipcdemo.IStudentListener;
interface IStudentManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void addStudent(in Student student);

    List<Student> getStudentList();

    void registerListener(in IStudentListener listener);
    void unregisterListener(in IStudentListener listener);
}