from PIL import Image

im = Image.open("game_over.png")
pix = im.load()

rows = []
for x in range(60):
    row = []
    for y in range(60):
        pix_boolean = (
            1 if pix[x, y] != (0, 0, 0) or not x or not y or x == 59 or y == 59 else 0
        )
        row.append(pix_boolean)
    rows.append(row)


java_format = str(rows).replace("[", "{").replace("]", "}")
print(java_format)
