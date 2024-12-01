// MealDetailScreen.kt
package com.example.project_meal_test.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.example.project_meal_test.R
import com.example.project_meal_test.viewmodel.MealViewModel

// MealDetailScreen.kt
@Composable
fun MealDetailScreen(mealId: Int, viewModel: MealViewModel = viewModel()) {
    val mealFlow = viewModel.findMealById(mealId)
    val meal by mealFlow.collectAsState(initial = null)

    meal?.let { meal ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 이미지 로드 로직
            val imageUri = meal.imageUri

            val isResourceId = try {
                imageUri.toInt()
                true
            } catch (e: NumberFormatException) {
                Log.d("MealDetailScreen", "이미지 URI가 리소스 ID가 아닙니다.")
                false
            }

            if (isResourceId) {

                val resourceId = imageUri.toInt()
                // 리소스 ID가 유효한지 확인
                val context = LocalContext.current
                val isResourceValid = try {
                    val drawable = context.resources.getDrawable(resourceId, context.theme)
                    drawable is android.graphics.drawable.BitmapDrawable
                } catch (e: Exception) {
                    false
                }

                if (isResourceValid) {
                    // 만약 이미지 URI가 유효한 리소스 ID라면
                    Log.d("MealDetailScreen", "이미지 URI가 유효한 리소스 ID입니다.")
                    Image(
                        painter = painterResource(id = imageUri.toInt()),
                        contentDescription = null,
                        modifier = Modifier
                            .size(300.dp)
                            .border(4.dp, Color.Gray, RoundedCornerShape(16.dp)) // 둥근 테두리와 두께 조정
                            .padding(8.dp)
                    )
                } else {
                    // 리소스 ID가 유효하지 않거나 이미지가 아닌 경우 기본 이미지와 에러 메시지 표시
                    Log.d("MealDetailScreen", "이미지 URI가 유효하지 않거나 이미지가 아닙니다. 기본 이미지를 사용합니다.")
                    Image(
                        painter = painterResource(id = R.drawable.default_image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(300.dp)
                            .border(4.dp, Color.Gray, RoundedCornerShape(16.dp)) // 둥근 테두리와 두께 조정
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "이미지 링크: $imageUri",
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }
            } else {
                // 만약 이미지 URI가 URL이라면 (리소스 ID로 변환 실패)
                Log.d("MealDetailScreen", "이미지 URI가 URL입니다.")
                // 이미지 로딩과 관련된 UI 구성
                SubcomposeAsyncImage(
                    model = meal.imageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp)
                        .border(4.dp, Color.Gray, RoundedCornerShape(16.dp))
                        .padding(8.dp),
                    loading = {
                        // 로딩 중일 때 로딩 인디케이터 표시
                        CircularProgressIndicator(modifier = Modifier.size(48.dp))
                    },
                    error = {
                        // 로드 실패 시 기본 이미지 및 텍스트 표시
                        Log.d("MealDetailScreen", "이미지 URI가 유효하지 않거나 이미지가 아닙니다. 기본 이미지를 사용합니다.")
                        Image(
                            painter = painterResource(id = R.drawable.default_image),
                            contentDescription = null,
                            modifier = Modifier
                                .size(300.dp)
                                .border(4.dp, Color.Gray, RoundedCornerShape(16.dp)) // 둥근 테두리와 두께 조정
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "이미지 링크: $imageUri",
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                )

                // 이미지 링크가 비어 있거나 URL 이미지 로드에 실패한 경우 URL 텍스트로 표시
                if (imageUri.isEmpty()) {
                    Text(
                        text = "이미지 링크: $imageUri",
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 음식 정보 텍스트들
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "음식 : ${meal.foodName}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "식당 : ${meal.place}",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "종류 : ${meal.type}",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "비용 : ${meal.cost}원",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 추가 정보 - 날짜, 소감, 칼로리량
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(2.dp, Color.LightGray, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "날짜 : ${meal.date}",
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "소감 : ${meal.review}",
                    fontSize = 18.sp,
                    color = Color.Black,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "칼로리량 : ${meal.calories} Kcal",
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    } ?: run {
        // 데이터가 없는 경우
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "식사 상세 정보를 찾을 수 없습니다.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red
            )
        }
    }
}
