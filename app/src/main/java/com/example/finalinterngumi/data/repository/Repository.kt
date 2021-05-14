package com.example.finalinterngumi.data.repository

import com.example.finalinterngumi.data.model.Fruit

interface Repository{
   suspend fun getData(get: (Fruit) -> Unit)
}