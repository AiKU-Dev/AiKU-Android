package com.hyunjung.aiku.core.terms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.ui.component.common.AikuTopAppBarDefaults
import com.hyunjung.aiku.core.ui.component.common.AikuTopAppBarWithNavigation
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme

@Composable
fun TermsDetailScreen(
    termsType: TermsType,
    modifier: Modifier = Modifier
) {
    val localContext = LocalContext.current
    val termsText = remember(termsType) {
        localContext.resources.openRawResource(termsType.resId).bufferedReader()
            .use { it.readText() }
    }

    TermsDetailScreen(
        title = termsType.title,
        content = termsText,
        modifier = modifier
    )
}

@Composable
private fun TermsDetailScreen(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AikuTopAppBarWithNavigation(
            title = title,
            colors = AikuTopAppBarDefaults.topAppBarColors(
                containerColor = AiKUTheme.colors.white
            )
        )
        Box(
            modifier = Modifier
                .padding(28.dp)
                .verticalScroll(scrollState)
        ) {
            AikuText(
                text = content,
                style = AiKUTheme.typography.caption1
            )
        }
    }
}

@Preview(showBackground = true, name = "Terms Detail Preview")
@Composable
private fun TermsDetailScreenPreview() {
    AikuPreviewTheme {
        TermsDetailScreen(
            title = "서비스 이용약관",
            content = """
            제1장 총칙

            제1조 (목적)
            이 약관은 "AiKU"(이하 '회사')가 제공하는 서비스의 이용 조건, 절차, 회사와 이용자의 권리 및 의무를 규정함을 목적으로 합니다.
            
            제2조 (용어의 정의)
            1. "이용자"란 회사의 서비스에 접속하여 이 약관에 따라 서비스를 이용하는 자를 말합니다.
            2. "회원"이란 개인정보를 제공하고 회사가 발급한 ID로 서비스를 이용하는 자를 말합니다.
            3. "비회원"이란 회원가입 없이 서비스를 이용하는 자를 말합니다.
            4. "ID"란 회원의 식별과 서비스 이용을 위해 부여된 문자 또는 숫자의 조합입니다.
            5. "비밀번호"란 개인정보 보호 및 본인 확인을 위해 회원이 설정한 문자와 숫자의 조합입니다.
            
            제3조 (약관의 효력 및 변경)
            1. 본 약관은 회사 홈페이지에 게시함으로써 효력이 발생합니다.
            2. 회사는 관련 법령을 위배하지 않는 범위에서 약관을 개정할 수 있으며, 개정 시 변경 내용을 적용일 7일 전부터 공지합니다.
            3. 이용자가 변경 약관에 명시적으로 거부 의사를 표시하지 않으면 동의한 것으로 간주합니다.
            
            제4조 (약관 외 준칙)
            약관에서 정하지 않은 사항은 관계 법령 및 회사의 서비스 안내에 따릅니다.
            
            제2장 이용계약
            
            제5조 (이용계약의 성립)
            서비스 이용계약은 이용자가 약관에 동의하고 회원가입 양식을 제출하며, 회사가 이를 승낙함으로써 성립합니다.
            
            제6조 (회원가입)
            회원은 정해진 양식에 따라 개인정보를 입력하여 가입하며, 허위 정보를 기재할 경우 서비스 이용이 제한될 수 있습니다.
            
            제7조 (개인정보 보호)
            회사는 관련 법령에 따라 회원의 개인정보를 보호하며, 개인정보처리방침에 따릅니다.
            
            제8조 (가입 거절 및 취소)
            다음의 경우 회사는 가입을 거절하거나 사후에 취소할 수 있습니다:
            - 허위 정보를 기재한 경우
            - 타인의 정보를 도용한 경우
            - 서비스 이용 목적이 부적절한 경우
            
            제9조 (ID의 부여 및 관리)
            1. 회원 ID는 변경이 불가하며, 부득이한 사유가 있을 경우 해지 후 재가입해야 합니다.
            2. 개인정보가 변경되었을 경우, 회원은 이를 즉시 수정해야 하며 미수정으로 인한 책임은 본인에게 있습니다.
            
            제3장 권리와 의무
            
            제10조 (회사의 의무)
            1. 회사는 지속적이고 안정적인 서비스 제공을 위해 최선을 다합니다.
            2. 개인정보 보호를 위해 보안 시스템을 구축하고 관련 정책을 준수합니다.
            
            제11조 (회원의 의무)
            1. 회원은 실명으로 정확한 정보를 입력해야 하며, 타인의 정보를 도용할 수 없습니다.
            2. ID와 비밀번호 관리 책임은 회원에게 있으며, 이를 타인에게 양도하거나 공유할 수 없습니다.
            3. 회원은 서비스 이용 시 관계 법령과 본 약관을 준수해야 합니다.
            
            제4장 서비스 이용
            
            제12조 (서비스 이용 시간)
            서비스는 연중무휴, 1일 24시간 제공되나, 점검이나 시스템 장애 시에는 중단될 수 있습니다.
            
            제13조 (콘텐츠 저작권)
            1. 회사가 게시한 콘텐츠의 저작권은 회사에 있으며, 무단 복제를 금합니다.
            2. 회원이 등록한 게시물의 저작권은 회원에게 있으나, 회사는 해당 게시물을 무료로 사용할 수 있는 권리를 가집니다.
            
            제14조 (서비스의 변경 및 중단)
            1. 회사는 서비스의 일부 또는 전부를 사전 공지 후 변경 또는 중단할 수 있습니다.
            2. 불가항력으로 인해 공지하지 못한 경우, 사후에 통지할 수 있습니다.
            
            제5장 계약 해지
            
            제15조 (이용계약 해지)
            1. 회원은 언제든지 마이페이지 등을 통해 계약 해지를 요청할 수 있습니다.
            2. 해지 시 관련 법령에 따라 일정 기간 정보를 보관할 수 있으며, 게시물은 삭제되지 않을 수 있습니다.
            
            제16조 (이용제한)
            다음 사유가 발생할 경우 회사는 회원의 서비스 이용을 제한하거나 계약을 해지할 수 있습니다:
            - 2년 이상 미사용
            - 약관 또는 법령 위반
            - 타인의 권리 침해 또는 서비스 운영 방해
            
            제6장 기타
            
            제17조 (손해배상)
            무료 서비스 이용과 관련된 손해에 대해 회사는 고의 또는 중대한 과실이 없는 한 책임을 지지 않습니다.
            
            제18조 (관할법원)
            서비스 이용과 관련한 분쟁은 민사소송법에 따른 관할 법원의 판결에 따릅니다.
            
            [부칙]
            본 약관은 2016년 12월 30일부터 시행됩니다.
        """.trimIndent()
        )
    }
}