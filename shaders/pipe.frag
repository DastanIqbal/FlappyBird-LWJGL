#version 330 core

layout (location=0) out vec4 color;

in DATA
{
    vec2 tc;
}fs_in;

uniform sampler2D tex;
uniform int top;

void main(){

    vec2 newtc=fs_in.tc;
    if(top==1)
        newtc.y = 1.0 - newtc.y;

    color=texture(tex,newtc);
    if(color.w < 1.0)
          discard;
}