class CreateIngredients < ActiveRecord::Migration
  def change
    create_table :ingredients do |t|
      t.string :name
      t.references :item

      t.timestamps
    end
    add_index :ingredients, :item_id
  end
end
