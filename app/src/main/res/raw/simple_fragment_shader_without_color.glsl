// 定义了浮点数据的默认精度，这里是中等精度
precision mediump float;
uniform vec4 u_Color;
void main() {
    // 一定要给gl_FragColor赋值，OpenGL会使用gl_FragColor作为当前片段的最终颜色
    gl_FragColor = u_Color;
}
