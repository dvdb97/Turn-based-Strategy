vec4 color() {

	vec4 grey = vec4(0.3f, 0.3f, 0.3f, 1f);
	vec4 green = vec4(0f, 0.3f, 0f, 1f);
	vec4 white = vec4(0.9f, 0.9f, 0.9f, 1f);

	float z = fs_in.fragCoordModelSpace.z;

	float minZ = 622f;
	float maxZ = 3727f;

	if (z > 0.8f)
		return white;

	if (z > 0.6f)
		return mix(white, grey, 5 * (0.8f - z));

	if (z > 0.5f)
		return grey;

	if (z > 0.2f)
		return mix(grey, green, 5 * (0.5 - z));

	vec4 col = mix(green, grey, pow((1+z) / 2, 3));

	return col;
}
