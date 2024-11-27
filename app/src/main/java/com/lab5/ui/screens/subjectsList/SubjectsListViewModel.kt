package com.lab5.ui.screens.subjectsList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab5.data.db.Lab5Database
import com.lab5.data.entity.SubjectEntity
import com.lab5.data.entity.SubjectLabEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SubjectsListViewModel(
    private val database: Lab5Database
) : ViewModel() {

    /**
    Flow - the (container, channel, observer), can accept and move data from producer to consumers
    StateFlow - the flow which also store data.
    MutableStateFlow - the stateFlow which can accept data (which you can fill)

    _subjectListStateFlow - private MutableStateFlow - ViewModel (add new data here)
    subjectListStateFlow - public StateFlow - ComposeScreen (read only data on screen)
     */
    private val _subjectListStateFlow = MutableStateFlow<List<SubjectEntity>>(emptyList())
    val subjectListStateFlow: StateFlow<List<SubjectEntity>>
        get() = _subjectListStateFlow


    /**
    Init block of ViewModel - invokes once the ViewModel is created
     */
    init {
        viewModelScope.launch {
            val subjects = database.subjectsDao.getAllSubjects()
            if (subjects.isEmpty()) {
                preloadData()
            } else {
                _subjectListStateFlow.value = subjects
            }
            Log.d("SubjectsListViewModel", "Subjects loaded: ${_subjectListStateFlow.value}")
        }
    }

    private suspend fun preloadData() {
        // List of subjects
        val listOfSubject = listOf(
            SubjectEntity(id = 1, title = "Основи штучного інтелекту"),
            SubjectEntity(id = 2, title = "Хмарні технології та сервіси"),
            SubjectEntity(id = 3, title = "Кібербезпека і захист даних"),
            SubjectEntity(id = 4, title = "Розробка веб-додатків"),
        )
        // List of labs
        val listOfSubjectLabs = listOf(
            // Основи штучного інтелекту
            SubjectLabEntity(
                id = 1,
                subjectId = 1,
                title = "Вступ до машинного навчання",
                description = "Вивчити основи машинного навчання та побудувати першу модель класифікації.",
                comment = "Дедлайн 10.01",
            ),
            SubjectLabEntity(
                id = 2,
                subjectId = 1,
                title = "Реалізація нейронної мережі",
                description = "Побудувати просту нейронну мережу для розпізнавання рукописних цифр.",
                comment = "Захист у четвер",
                isCompleted = true
            ),
            // Хмарні технології та сервіси
            SubjectLabEntity(
                id = 3,
                subjectId = 2,
                title = "Налаштування хмарної інфраструктури",
                description = "Розгорнути сервер у хмарі AWS і налаштувати базовий стек веб-додатка.",
                comment = "",
                isCompleted = true
            ),
            SubjectLabEntity(
                id = 4,
                subjectId = 2,
                title = "Контейнеризація з Docker",
                description = "Створити Docker-контейнер для розроблюваного веб-додатка.",
                comment = "Тестування в п’ятницю",
                inProgress = true
            ),
            // Кібербезпека і захист даних
            SubjectLabEntity(
                id = 5,
                subjectId = 3,
                title = "Шифрування даних",
                description = "Реалізувати алгоритм шифрування AES для захисту файлів.",
                comment = "",
                isCompleted = true
            ),
            SubjectLabEntity(
                id = 6,
                subjectId = 3,
                title = "Пошук вразливостей",
                description = "Сканувати сервер на вразливості за допомогою OWASP ZAP.",
                comment = "Захист у понеділок",
                isCompleted = true
            ),
            // Розробка веб-додатків
            SubjectLabEntity(
                id = 7,
                subjectId = 4,
                title = "Розробка REST API",
                description = "Створити REST API для блогу з використанням Spring Boot.",
                comment = "Протестувати ендпоінти",
                isCompleted = true
            ),
            SubjectLabEntity(
                id = 8,
                subjectId = 4,
                title = "Інтеграція фронтенду та бекенду",
                description = "Інтегрувати фронтенд (React) із бекендом через API.",
                comment = "Завершити до середи",
                inProgress = true
            ),
        )

        listOfSubject.forEach { subject ->
            database.subjectsDao.addSubject(subject)
        }
        listOfSubjectLabs.forEach { lab ->
            database.subjectLabsDao.addSubjectLab(lab)
        }
    }
}