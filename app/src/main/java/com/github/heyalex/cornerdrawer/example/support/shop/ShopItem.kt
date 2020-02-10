package com.github.heyalex.cornerdrawer.example.support.shop

data class ShopItem(
    val id: Long,
    val name: String,
    val description: String = lorem,
    val price: Long = 66L,
    val url: String
)

val lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

val items = listOf(
    ShopItem(
        id = 0,
        name = "Ceramic Mug",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/dinner_set.jpg?raw=true"
    ),
    ShopItem(
        id = 1,
        name = "Cookware set",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/ceramic%20mug.jpg?raw=true"
    ),
    ShopItem(
        id = 2,
        name = "Dinner Set",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/dinner_set.jpg?raw=true"
    ),
    ShopItem(
        id = 3,
        name = "Water Jug",
        url = "https://www.save-on-crafts.com/media/catalog/product/cache/3/image/650x/4ab5f96ba821f6793c9aaccc3424405a/l/i/linen-squares-20-x-20-fringe-edge-12-pieces-3.jpg"
    ),
    ShopItem(
        id = 4,
        name = "Ceramic Mug",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/dinner_set.jpg?raw=true"
    ),
    ShopItem(
        id = 5,
        name = "Ceramic Mug",
        url = "https://www.save-on-crafts.com/media/catalog/product/cache/3/image/650x/4ab5f96ba821f6793c9aaccc3424405a/l/i/linen-squares-20-x-20-fringe-edge-12-pieces-3.jpg"
    ),
    ShopItem(
        id = 6,
        name = "Ceramic Mug",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/ceramic%20mug.jpg?raw=true"
    ),
    ShopItem(
        id = 7,
        name = "Ceramic Mug",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/linen%20napkins.jpeg?raw=true"
    ),
    ShopItem(
        id = 8,
        name = "Ceramic Mug",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/linen%20napkins.jpeg?raw=true"
    ),
    ShopItem(
        id = 9,
        name = "Ceramic Mug",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/dinner_set.jpg?raw=true"
    ),
    ShopItem(
        id = 10,
        name = "Cookware set",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/ceramic%20mug.jpg?raw=true"
    ),
    ShopItem(
        id = 11,
        name = "Dinner Set",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/dinner_set.jpg?raw=true"
    ),
    ShopItem(
        id = 12,
        name = "Water Jug",
        url = "https://www.save-on-crafts.com/media/catalog/product/cache/3/image/650x/4ab5f96ba821f6793c9aaccc3424405a/l/i/linen-squares-20-x-20-fringe-edge-12-pieces-3.jpg"
    ),
    ShopItem(
        id = 13,
        name = "Ceramic Mug",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/dinner_set.jpg?raw=true"
    ),
    ShopItem(
        id = 14,
        name = "Ceramic Mug",
        url = "https://www.save-on-crafts.com/media/catalog/product/cache/3/image/650x/4ab5f96ba821f6793c9aaccc3424405a/l/i/linen-squares-20-x-20-fringe-edge-12-pieces-3.jpg"
    ),
    ShopItem(
        id = 15,
        name = "Ceramic Mug",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/ceramic%20mug.jpg?raw=true"
    ),
    ShopItem(
        id = 16,
        name = "Ceramic Mug",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/linen%20napkins.jpeg?raw=true"
    ),
    ShopItem(
        id = 17,
        name = "Ceramic Mug",
        url = "https://github.com/HeyAlex/BottomDrawer/blob/master/raw/linen%20napkins.jpeg?raw=true"
    )
)

fun getShopItem(id: Long): ShopItem {
    return items.first { it.id == id }
}

