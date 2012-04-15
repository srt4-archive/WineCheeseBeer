class CreateItemIngredients < ActiveRecord::Migration
  def change
    create_table :item_ingredients do |t|
      t.references :item
      t.references :ingredient

      t.timestamps
    end
    add_index :item_ingredients, :item_id
    add_index :item_ingredients, :ingredient_id
  end
end
