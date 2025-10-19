package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.vacancy.Contacts
import ru.practicum.android.diploma.presentation.details.models.DetailsScreenState
import ru.practicum.android.diploma.presentation.details.viewmodel.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.models.VacancyInfo
import ru.practicum.android.diploma.ui.components.ErrorMessage
import ru.practicum.android.diploma.ui.components.Logo
import ru.practicum.android.diploma.ui.components.ProgressBar
import ru.practicum.android.diploma.ui.components.topbar.SimpleTopBarWithBackIcon
import ru.practicum.android.diploma.ui.components.topbar.VacancyDetailsTopBar
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.CustomColors
import ru.practicum.android.diploma.ui.theme.CustomTypography
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

@Composable
fun VacancyScreen(
    vacancyId: String,
    onBackClick: () -> Unit,
    viewModel: VacancyDetailsViewModel = koinViewModel(
        parameters = { parametersOf(vacancyId) }
    )
) {
    val colors = LocalCustomColors.current
    val modifier = Modifier

    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val isFavorite = (state as? DetailsScreenState.Content)?.data?.isFavorite ?: false

    Scaffold(
        topBar = {
            when (state) {
                is DetailsScreenState.Content, DetailsScreenState.Empty -> {
                    VacancyDetailsTopBar(
                        isFavorite = isFavorite,
                        onNavigationIconClick = onBackClick,
                        onShareIconClick = { viewModel.onShareClick() },
                        onFavoriteIconClick = { viewModel.onFavoriteClick() }
                    )
                }

                DetailsScreenState.Loading, DetailsScreenState.Error, DetailsScreenState.InternetError -> {
                    SimpleTopBarWithBackIcon(
                        stringResource(R.string.vacancy_details_screen_title),
                        onNavigationIconClick = onBackClick
                    )
                }
            }
        },
        containerColor = colors.vacancyDetailsColors.background,
        contentColor = colors.vacancyDetailsColors.text,
        content = {
            Box(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                when (val curState = state) {
                    DetailsScreenState.Loading -> {
                        ProgressBar()
                    }

                    DetailsScreenState.Empty -> {
                        ErrorMessage(
                            title = stringResource(R.string.the_vacancy_was_not_found_or_was_deleted),
                            imageId = R.drawable.im_lack_of_vacancy,
                            modifier = modifier.fillMaxSize()
                        )
                    }

                    DetailsScreenState.Error -> {
                        ErrorMessage(
                            title = stringResource(R.string.server_error),
                            imageId = R.drawable.im_server_error_cat,
                            modifier = modifier.fillMaxSize()
                        )
                    }

                    DetailsScreenState.InternetError -> {
                        ErrorMessage(
                            title = stringResource(R.string.im_bad_connection_description),
                            imageId = R.drawable.im_bad_connection,
                            modifier = modifier.fillMaxSize()
                        )
                    }

                    is DetailsScreenState.Content -> {
                        VacancyContent(
                            vacancy = curState.data,
                            onEmailClick = { viewModel.onEmailClick() },
                            onPhoneClick = { phone -> viewModel.onPhoneClick(phone) }
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun VacancyContent(
    modifier: Modifier = Modifier,
    vacancy: VacancyInfo,
    onEmailClick: () -> Unit,
    onPhoneClick: (String) -> Unit
) {
    Column(
        modifier.verticalScroll(rememberScrollState())
    ) {
        VacancyTitle(
            name = vacancy.name,
            salary = vacancy.salary
        )

        Spacer(modifier.height(16.dp))

        VacancyCard(
            logo = vacancy.employerLogo,
            employer = vacancy.employerName,
            address = vacancy.address ?: vacancy.area

        )

        Spacer(modifier.height(24.dp))

        VacancyExperience(
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment
        )

        Spacer(modifier.height(32.dp))

        VacancyInfo(
            description = vacancy.description,
            skills = vacancy.skills?.toImmutableList(),
            contacts = vacancy.contacts,
            onEmailClick = onEmailClick,
            onPhoneClick = onPhoneClick
        )
    }
}

@Composable
private fun VacancyTitle(
    modifier: Modifier = Modifier,
    name: String,
    salary: String,
    typography: CustomTypography = LocalTypography.current
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp),
    ) {
        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = typography.vacancyDetailBigTitle
        )

        Spacer(modifier.height(4.dp))

        Text(
            text = salary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = typography.vacancyDetailTitle
        )
    }
}

@Composable
private fun VacancyCard(
    modifier: Modifier = Modifier,
    logo: String?,
    employer: String,
    address: String,
    typography: CustomTypography = LocalTypography.current,
    colors: CustomColors = LocalCustomColors.current
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.vacancyInfoCard.background,
            contentColor = colors.vacancyInfoCard.text
        )
    ) {
        Row(
            modifier = modifier.padding(16.dp)
        ) {
            Logo(logo)

            Spacer(modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = employer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = typography.vacancyInfoCardTitle
                )

                Text(
                    text = address,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = typography.vacancyInfoCardText
                )
            }
        }
    }
}

@Composable
private fun VacancyExperience(
    modifier: Modifier = Modifier,
    experience: String,
    schedule: String?,
    employment: String?,
    typography: CustomTypography = LocalTypography.current
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        Text(
            text = stringResource(R.string.required_experience),
            style = typography.vacancyDetailSmallTitle
        )

        Spacer(modifier.height(4.dp))

        Text(
            text = experience,
            style = typography.vacancyDetailText
        )

        Spacer(modifier.height(8.dp))

        if (!schedule.isNullOrBlank() || !employment.isNullOrBlank()) {
            val textParts = listOfNotNull(
                employment?.takeIf { it.isNotBlank() },
                schedule?.takeIf { it.isNotBlank() }
            )
            Text(
                text = textParts.joinToString(", "),
                style = typography.vacancyDetailText
            )
        }
    }
}

@Composable
private fun VacancyInfo(
    modifier: Modifier = Modifier,
    description: String?,
    skills: ImmutableList<String>?,
    contacts: Contacts?,
    onEmailClick: () -> Unit,
    onPhoneClick: (String) -> Unit
) {
    if (!description.isNullOrBlank()) {
        VacancyDescription(description = description)
        Spacer(modifier.height(24.dp))
    }

    if (!skills.isNullOrEmpty()) {
        VacancySkills(skills = skills)
        Spacer(modifier.height(24.dp))
    }

    if (contacts != null) {
        VacancyContacts(
            contacts = contacts,
            onEmailClick = onEmailClick,
            onPhoneClick = onPhoneClick
        )
    }
}

@Composable
private fun VacancyDescription(
    modifier: Modifier = Modifier,
    description: String,
    typography: CustomTypography = LocalTypography.current
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        Text(
            text = stringResource(R.string.vacancy_description),
            style = typography.vacancyDetailTitle
        )

        Spacer(modifier.height(16.dp))

        Text(
            text = description,
            style = typography.vacancyDetailText
        )
    }
}

@Composable
private fun VacancySkills(
    modifier: Modifier = Modifier,
    skills: ImmutableList<String>,
    typography: CustomTypography = LocalTypography.current
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        Text(
            text = stringResource(R.string.key_skills),
            style = typography.vacancyDetailTitle
        )

        Spacer(modifier.height(16.dp))

        skills.forEach { skill ->
            Text(
                text = skill,
                style = typography.vacancyDetailText
            )
        }
    }
}

@Composable
private fun VacancyContacts(
    modifier: Modifier = Modifier,
    contacts: Contacts,
    onEmailClick: () -> Unit,
    onPhoneClick: (String) -> Unit,
    typography: CustomTypography = LocalTypography.current
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        Text(
            text = stringResource(R.string.contacts),
            style = typography.vacancyDetailTitle
        )

        Spacer(modifier.height(16.dp))

        if (!contacts.name.isNullOrBlank()) {
            ContactItem(
                title = stringResource(R.string.contact_name),
                value = contacts.name
            )
        }

        if (!contacts.email.isNullOrBlank()) {
            ContactItem(
                title = stringResource(R.string.email),
                value = contacts.email,
                onItemClick = onEmailClick
            )
        }

        if (!contacts.phone.isNullOrEmpty()) {
            contacts.phone.forEach { phone ->
                if (!phone.formatted.isNullOrBlank()) {
                    ContactItem(
                        title = stringResource(R.string.phone),
                        value = phone.formatted,
                        onItemClick = { onPhoneClick(phone.formatted) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onItemClick: () -> Unit = {},
    typography: CustomTypography = LocalTypography.current
) {
    Text(
        text = title,
        style = typography.vacancyDetailSmallTitle
    )

    Spacer(modifier.height(4.dp))

    Text(
        modifier = modifier.clickable { onItemClick() },
        text = value,
        style = typography.vacancyDetailText
    )

    Spacer(modifier.height(16.dp))
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun TitlePreview() {
    AppTheme(darkTheme = true) {
        VacancyTitle(
            name = "Android-разработчик",
            salary = "от 100 000 до 200 000 ₽"
        )
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun VacancyCardPreview() {
    AppTheme(darkTheme = true) {
        VacancyCard(
            logo = "",
            employer = "Practicum",
            address = "Moscow"
        )
    }
}

private val vacancy = VacancyInfo(
    id = "b4cb93e5-1266-45b1-a1dd-43d193bd0631",
    name = "DevOps Engineer в Google",
    description = """
        Если ты начинающий DevOps-инженер и хочешь развиваться в динамично развивающейся 
        аккредитованной IT-компании, мы ждем тебя! Мы предоставляем высококвалифицированных 
        специалистов для реализации проектов различной сложности и помогаем партнерам успешно 
        завершать их, обеспечивая высокое качество услуг.

        Что мы предлагаем:

        Полностью удаленный формат работы с гибким графиком (5/2 с 9:00 до 19:00).

        Современные инструменты и подходы для автоматизации тестирования.
        Возможности для профессионального роста и обучения.
        Дружелюбную атмосферу в коллективе и поддержку коллег.
        Конкурентоспособную заработную плату, начиная от 70 тысяч рублей.
        Неоплачиваемая стажировка в течение 2–3 месяцев.

        Обязанности:
        Сопровождать тестовые окружения.
        Переносить сервисы в Kubernetes.
        Автоматизировать сборку и доставку приложений (CI/CD).
        Писать новые роли Ansible.
        Поддерживать команды разработки и тестирования.

        От тебя ждём:
        Умение работать с:
        - Jenkins
        - Gitlab CI
        - Ansible
        - Kubernetes
        - Nginx
        - Vault
        - Docker, Docker-compose.
    """.trimIndent(),
    salary = "от 8 000 до 18 000 HKD",
    address = null,
    experience = "Нет опыта",
    schedule = null,
    employment = "Полная занятость",
    contacts = Contacts(
        id = "3",
        name = "Смирнов Алексей Иванович",
        email = "123@gmail.com",
        phone = null,
    ),
    employerName = "Google",
    employerLogo = null,
    area = "Грузия",
    skills = listOf("C++", "TypeScript", "PHP", "Ruby", "Swift"),
    url = null,
    industry = "Услуги для населения",
    isFavorite = false
)

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun VacancyContentPreview() {
    AppTheme(darkTheme = false) {
        VacancyContent(vacancy = vacancy, onEmailClick = {}, onPhoneClick = {})
    }
}
