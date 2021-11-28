// GLSL是OpenGL的着色语言，语法结构与C语言相似
attribute vec4 a_Position;
// 添加一个新的uniform定义为u_Matrix，并把它定义为一个mat4类型，代表一个4*4的矩阵
uniform mat4 u_Matrix;
// 着色器的主要入口点
void main() {
    // gl_Position一定要赋值，OpenGL会把gl_Position存储的值作为当前顶点的最终位置
    gl_Position = u_Matrix*a_Position;
}