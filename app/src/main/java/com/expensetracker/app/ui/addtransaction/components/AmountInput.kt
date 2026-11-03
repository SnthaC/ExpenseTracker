package com.expensetracker.app.ui.addtransaction.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.expensetracker.app.ui.theme.AppColors

@Composable
fun AmountInput(
    amount: String,
    onAmountChange: (String) -> Unit,
    amountError: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(AppColors.LightLavender)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "Amount",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF4B4658),
            modifier = Modifier.padding(start = 5.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Color(0xFFDED9E8))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "£",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(40.dp)
                        .background(Color(0xFFDED9E8))
                )

                val amountTextStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )

                BasicTextField(
                    value = amount,
                    onValueChange = { input ->
                        if (input.isEmpty() || input.matches(
                                Regex(
                                    "^\\d*\\.?\\d{0,2}$"
                                )
                            )
                        ) {
                            onAmountChange(input)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    textStyle = amountTextStyle.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF252333)
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    decorationBox = { innerTextField ->
                        if (amount.isEmpty()) {
                            Text(
                                text = "0.00",
                                style = amountTextStyle,
                                color = Color(0xFFAAA6B5)
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }

        Text(
            text = if (amountError) "Enter a valid amount"
            else "Enter transaction amount",
            style = MaterialTheme.typography.bodySmall,
            color = if (amountError) MaterialTheme.colorScheme.error
            else Color(0xFF8B8797),
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}