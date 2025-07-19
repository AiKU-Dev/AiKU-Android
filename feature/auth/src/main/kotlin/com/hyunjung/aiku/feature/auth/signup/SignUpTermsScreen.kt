package com.hyunjung.aiku.feature.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuCheckableSurface
import com.hyunjung.aiku.core.designsystem.component.AikuIcon
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.TermsType
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme
import com.hyunjung.aiku.feature.auth.R
import com.hyunjung.aiku.core.ui.R as UiR

@Composable
internal fun SignUpTermsScreen(
    onTermsAgreed: (List<TermsType>) -> Unit,
    onTermsClick: (TermsType) -> Unit,
    modifier: Modifier = Modifier
) {
    val termsTypes = TermsType.entries
    val checkedStates = rememberSaveable(
        saver = mapSaver(
            save = { it.map { entry -> entry.key.name to entry.value }.toMap() },
            restore = { restoredMap ->
                mutableStateMapOf<TermsType, Boolean>().apply {
                    restoredMap.forEach { (key, value) ->
                        put(TermsType.valueOf(key), value as Boolean)
                    }
                }
            }
        )
    ) {
        mutableStateMapOf<TermsType, Boolean>().apply {
            TermsType.entries.forEach { this[it] = false }
        }
    }

    termsTypes.forEach { if (it !in checkedStates) checkedStates[it] = false }

    val allChecked = checkedStates.values.all { it }
    val allRequiredChecked = checkedStates
        .filterKeys { it.isRequired }
        .all { it.value }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 40.dp)
    ) {
        Spacer(Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(UiR.drawable.img_char_head_boy),
                contentDescription = null,
            )
            Image(
                painter = painterResource(UiR.drawable.img_text_hi),
                contentDescription = null,
            )
        }
        Spacer(Modifier.height(12.dp))
        AikuText(
            text = stringResource(R.string.feature_auth_signup_terms_header),
            style = AiKUTheme.typography.headline3G,
        )
        Spacer(Modifier.weight(1f))
        AllTermsAgreementItem(
            checked = allChecked,
            onCheckedChange = { isChecked ->
                termsTypes.forEach { termsType ->
                    checkedStates[termsType] = isChecked
                }
            }
        )
        Column(
            modifier = Modifier.padding(vertical = 40.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            termsTypes.forEach { termsType ->
                val label = stringResource(
                    id = if (termsType.isRequired) {
                        R.string.feature_auth_signup_terms_label_required
                    } else {
                        R.string.feature_auth_signup_terms_label_optional
                    },
                    termsType.title
                )

                TermsAgreementItem(
                    label = label,
                    checked = checkedStates[termsType] == true,
                    onCheckedChange = { isChecked ->
                        checkedStates[termsType] = isChecked
                    },
                    onTermsClick = { onTermsClick(termsType) },
                )
            }
        }

        AikuButton(
            onClick = { onTermsAgreed(checkedStates.filterValues { it }.keys.toList()) },
            enabled = allRequiredChecked,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            AikuText(
                text = stringResource(R.string.feature_auth_signup_terms_agree_start),
                style = AiKUTheme.typography.subtitle3SemiBold,
                color = AiKUTheme.colors.white
            )
        }
    }
}

@Composable
private fun AllTermsAgreementItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AikuCheckableSurface(
            checked = checked,
            onCheckedChange = onCheckedChange,
            color = if (checked) AiKUTheme.colors.green05 else AiKUTheme.colors.gray02,
            shape = RoundedCornerShape(4.dp),
        ) {
            AikuIcon(
                painter = AikuIcons.Check,
                tint = AiKUTheme.colors.white,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp)
            )
        }
        AikuText(
            text = stringResource(R.string.feature_auth_signup_terms_agree_all),
            style = AiKUTheme.typography.subtitle3,
        )
    }
}

@Composable
private fun TermsAgreementItem(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onTermsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AikuCheckableSurface(
            checked = checked,
            onCheckedChange = onCheckedChange
        ) {
            AikuIcon(
                painter = AikuIcons.Check,
                tint = if (checked) AiKUTheme.colors.green05 else AiKUTheme.colors.gray02,
                contentDescription = null
            )
        }

        AikuText(
            text = label,
            style = AiKUTheme.typography.body1SemiBold,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onTermsClick
            )
        )
    }
}

@Preview
@Composable
private fun TermsAgreementItemPreview() {
    AikuPreviewTheme {
        SignUpTermsScreen(
            onTermsAgreed = {},
            onTermsClick = {},
        )
    }
}