attribute vec4 a_Position;
// 因为有两个分量S坐标和T坐标，所以是vec2
attribute vec2 a_TextureCoordinates;
// 顶点着色器被插值的varying
varying vec2 v_TextureCoordinates;
// 着色器的主要入口点
void main() {
    v_TextureCoordinates = a_TextureCoordinates;
    // gl_Position一定要赋值，OpenGL会把gl_Position存储的值作为当前顶点的最终位置
    gl_Position = a_Position;
}