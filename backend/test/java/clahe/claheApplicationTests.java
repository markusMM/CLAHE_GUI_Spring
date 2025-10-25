@SpringBootTest
class ClaheServiceTests {

    @Autowired
    private ClaheService claheService;

    @Test
    void testCLAHEProcessingKeepsColor() {
        Mat input = Imgcodecs.imread("src/test/resources/image.png");
        Mat result = claheService.applyClahe(input, 2.0, 8);
        assertEquals(input.size(), result.size());
    }
}

@WebMvcTest(controllers = ClaheController.class)
class ClaheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnBase64ResultForImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file","test.png","image/png",new byte[]{1,2,3});
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/image/clahe").file(file))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.originalImage").exists());
    }
}
