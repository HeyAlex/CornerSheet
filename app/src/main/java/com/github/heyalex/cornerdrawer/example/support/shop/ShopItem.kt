package com.github.heyalex.cornerdrawer.example.support.shop

data class ShopItem(
    val id: Long,
    val name: String,
    val description: String = lorem,
    val price: Long = 66L,
    val url: String
)

val lorem =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

val items = listOf(
    ShopItem(
        id = 0,
        name = "Cutting boards",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/antique_cutting_boards.jpg?raw=true"
    ),
    ShopItem(
        id = 1,
        name = "Ceramic mugs",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/ceramic%20mug.jpg?raw=true"
    ),
    ShopItem(
        id = 2,
        name = "Dinner Set",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/dinner_set.jpg?raw=true"
    ),
    ShopItem(
        id = 3,
        name = "Ceramic jugs set",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/ceramic_jugs.jpg?raw=true"
    ),
    ShopItem(
        id = 4,
        name = "Water jugs set",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/water_jugs_2.jpg?raw=true"
    ),
    ShopItem(
        id = 5,
        name = "Tableware",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/tableware.jpg?raw=true"
    ),
    ShopItem(
        id = 6,
        name = "Dinnerware set 3",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/dinnerware_set_2.jpg?raw=true"
    ),
    ShopItem(
        id = 7,
        name = "Square dinner set",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/square_dinner_set.jpeg?raw=true"
    ),
    ShopItem(
        id = 8,
        name = "Wooden spoons",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/spoon_set.jpg?raw=true"
    ),
    ShopItem(
        id = 9,
        name = "Linen napkins",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/linen_napkins_2.jpg?raw=true"
    ),
    ShopItem(
        id = 10,
        name = "Dinnerware set 1",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/dinnerware_set_3.jpg?raw=true"
    ),
    ShopItem(
        id = 11,
        name = "Dinnerware set 2",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/dinner_set.jpg?raw=true"
    ),
    ShopItem(
        id = 12,
        name = "Ceramic Mug",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/ceramic%20mug.jpg?raw=true"
    ),
    ShopItem(
        id = 13,
        name = "Plate set",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/dinner_set_2.jpg?raw=true"
    ),
    ShopItem(
        id = 14,
        name = "Ceramic Mugs",
        url = "https://github.com/HeyAlex/CornerSheet/blob/master/raw/ceramic_mugs_2.jpg?raw=true"
    )
)

fun getShopItem(id: Long): ShopItem {
    return items.first { it.id == id }
}

