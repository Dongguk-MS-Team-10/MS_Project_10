package com.example.project_meal_test.ui.screen

// 기본 UI 요소
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import coil.compose.SubcomposeAsyncImage
import com.example.project_meal_test.R
import com.example.project_meal_test.data.Meal
import com.example.project_meal_test.viewmodel.MealViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun MealListScreen(
    viewModel: MealViewModel,
    onMealClick: (Int) -> Unit,     // 특정 식사를 클릭했을 때의 이벤트
    onAddMealClick: () -> Unit,      // 식사 추가 버튼 클릭 이벤트
    onAnalysisClick: () -> Unit,     // 식단 분석 버튼 클릭 이벤트
    meals: Flow<List<Meal>>          // 실제 식사 데이터를 리스트로 전달
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val mealList by meals.collectAsState(initial = emptyList())

        // 식단 분석 버튼
        androidx.compose.material3.Button(
            onClick = onAnalysisClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "식단 분석")
        }

        // 식사 추가 버튼
        androidx.compose.material3.Button(
            onClick = onAddMealClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "식사 추가")
        }

        Spacer(modifier = Modifier.height(16.dp)) // 간격 추가

        // 식사 리스트
        if (mealList.isEmpty()) {
            // 데이터가 없을 때 표시
            Text(
                text = "등록된 식사가 없습니다.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(mealList) { meal ->
                    MealItem(meal = meal, onClick = { onMealClick(meal.id) })
                }
            }
        }
    }
}

@Composable
fun MealItem(
    meal: Meal,                     // 단일 식사 데이터
    onClick: () -> Unit             // 클릭 이벤트
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 이미지 로딩 로직
            val imageUri = meal.imageUri
            val isResourceId = try {
                imageUri.toInt()
                true
            } catch (e: NumberFormatException) {
                false
            }

            if (isResourceId) {
                // 리소스 ID를 로드
                val resourceId = imageUri.toInt()
                val context = LocalContext.current

                val isResourceValid = try {
                    context.resources.getDrawable(resourceId, context.theme)
                    true
                } catch (e: Exception) {
                    false
                }

                if (isResourceValid) {
                    // 유효한 리소스 ID인 경우 이미지 표시
                    Image(
                        painter = painterResource(id = resourceId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                    )
                } else {
                    // 리소스 ID가 유효하지 않은 경우 기본 이미지 표시
                    Image(
                        painter = painterResource(id = R.drawable.default_image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                    )
                }
            } else {
                // URL로부터 이미지 로드
                SubcomposeAsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)),
                    loading = {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    },
                    error = {
                        // 로드 실패 시 기본 이미지 표시
                        Image(
                            painter = painterResource(id = R.drawable.default_image),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // 텍스트 정보
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = meal.foodName, style = MaterialTheme.typography.titleMedium, fontSize = 18.sp)
                Text(text = meal.place, style = MaterialTheme.typography.bodyMedium)
                Text(text = meal.date.toString(), style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.width(8.dp))

            // 비용 정보
            Text(
                text = "${meal.cost}원",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
