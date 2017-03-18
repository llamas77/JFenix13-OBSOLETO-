#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec2 vTexCoord0;
varying LOWP vec4 vColor;

uniform sampler2D u_texture;
uniform sampler2D u_light;
uniform vec2 resolution;

void main() {

    vec2 p = gl_FragCoord.xy / resolution.xy;

    vec4 lightCol = texture2D(u_light, p);
    vec4 texCol = texture2D(u_texture, vTexCoord0);

    //if (lightCol.a > 0.0) {
        //gl_FragColor = mix(vColor * texCol,texCol * lightCol, 1.0);
    //}
    //else {
        //gl_FragColor = texCol;
    //}

    if (lightCol.rgba == vec4(0.0)) {
        gl_FragColor = texCol;
    }
    else {
        gl_FragColor = vec4(texCol.rgb * lightCol.rgb, 1.0);
        //gl_FragColor = texCol * lightCol;
    }

    //gl_FragColor = texCol;



    //gl_FragColor = vColor * mix(texture2D(u_texture, vTexCoord0), texture2D(u_light, vTexCoordNew), 0.7);


    //gl_FragColor = vColor * texture2D(u_light, vTexCoord0);
}
