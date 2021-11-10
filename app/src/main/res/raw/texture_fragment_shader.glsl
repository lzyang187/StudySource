// 定义了浮点数据的默认精度，这里是中等精度
precision mediump float;
// 片段着色器通过u_TextureUnit接收实际的纹理数据，smapler2D这个变量类型是指一个二维纹理数据的数组
uniform sampler2D u_TextureUnit;
varying vec2 v_TextureCoordinates;
void main() {
    // 被插值的纹理坐标和纹理数据被传递给着色器函数texture2D，它会读入纹理中那个特定坐标处的颜色值
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
}
