// GLSL是OpenGL的着色语言，语法结构与C语言相似
attribute vec4 a_Position;
attribute vec4 a_Color;
// varying把给它的那些值进行混合，并把这些混合后的值发送给片段着色器
varying vec4 v_Color;
// 添加一个新的uniform定义为u_Matrix，并把它定义为一个mat4类型，代表一个4*4的矩阵
uniform mat4 u_Matrix;
// 着色器的主要入口点
void main() {
    v_Color = a_Color;
    // gl_Position一定要赋值，OpenGL会把gl_Position存储的值作为当前顶点的最终位置
//    gl_Position = a_Position;
    gl_Position = u_Matrix*a_Position;
    // 指定点的大小，点的矩形的边长为10
    gl_PointSize = 10.0;
}