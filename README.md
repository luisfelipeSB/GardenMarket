# **Garden Market | Casos de utilização:**


## Caso #1: Navegação pelos anúncios com carrinho

-   O utilizador direciona-se à secção de anúncios da plataforma
-   O utilizador pesquisa por um item que esteja disposto a comprar, quando encontrar um produto que lhe interesse, seleciona-o. O utilizador pode pesquisar por um item de diferentes maneiras:
	- Por título
	- Por categoria
- Com o item selecionado, o utilizador é levado à página do produto, e daqui tem algumas opções:
	- Visualiza o perfil do anunciante, vendo todos seus anúncios ativos
	- Adiciona o item ao seu carrinho de compras

## Caso #2: **Compra de um ou vários produtos no carrinho**

-   O utilizador direciona-se à secção do carrinho de compras da plataforma
-   [Pré condição] O utilizador tem um carrinho de compras não-vazio:
-   O utilizador vê uma lista dos itens adicionados ao carrinho, o preço individual de cada, e um preço total. Daqui, o utilizador tem algumas opções:
	-   Remove itens da lista
    -   Seleciona um item e é levado à página do produto
    -   Prossegue à compra de todos os itens no carrinho
		-   Se obter sucesso, recebe uma mensagem de transação bem feita
		-   Se não obter sucesso, transação é cancelada e recebe mensagem de transação não efetuada.

## Caso #3: **Adição de um anúncio**

-   O utilizador direciona-se à secção de adição de um anúncio da plataforma
-   O utilizador inicia o processo para adicionar um anúncio
    -   Dá um título ao anúncio
    -   Seleciona uma categoria
    -   Adiciona uma descrição
    -   Adiciona um preço
-   Se obter sucesso, recebe uma mensagem de aprovação do anúncio e o anúncio é adicionado a nossa database.
-   Se não obter sucesso, recebe uma mensagem de erro e é pedido que tente novamente.
